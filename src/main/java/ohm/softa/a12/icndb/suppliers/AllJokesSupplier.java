package ohm.softa.a12.icndb.suppliers;

import ohm.softa.a12.icndb.ICNDBApi;
import ohm.softa.a12.icndb.ICNDBService;
import ohm.softa.a12.model.JokeDto;
import ohm.softa.a12.model.ResponseWrapper;
import org.apache.commons.lang3.NotImplementedException;

import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * Supplier implementation to retrieve all jokes of the ICNDB in a linear way
 *
 * @author Peter Kurfer
 */

public final class AllJokesSupplier implements Supplier<ResponseWrapper<JokeDto>> {
	Integer countOfJokes;
	Integer nextJoke;

	/* ICNDB API proxy to retrieve jokes */
	private final ICNDBApi icndbApi;

	public AllJokesSupplier() {
		icndbApi = ICNDBService.getInstance();
		nextJoke = 0;
		try {
			countOfJokes = icndbApi.getJokeCount().get().getValue();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		/*
		* to determine when all jokes are iterated and the counters have to be reset */
	}

	@Override
	public ResponseWrapper<JokeDto> get() {

		while (true) {
			nextJoke++;
			try {
				if (nextJoke==countOfJokes) {
					nextJoke=1;
				}
			return icndbApi.getJoke(nextJoke).get();

			} catch (InterruptedException e) {
			} catch (ExecutionException e) {
			}
			catch (Exception e){

			}
		}
		/*
		 * note that there might be IDs that are not present in the database
		 * you have to catch an exception and continue if no joke was retrieved to an ID
		 * if you retrieved all jokes (count how many jokes you successfully fetched from the API)
		 * reset the counters and continue at the beginning */
	}

}
