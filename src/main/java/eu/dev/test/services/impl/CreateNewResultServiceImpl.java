package eu.dev.test.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.dev.test.models.SortedResult;
import eu.dev.test.models.SortedResultDao;
import eu.dev.test.services.CreateNewResultService;

@Service
public class CreateNewResultServiceImpl implements CreateNewResultService {

    @Autowired
    private SortedResultDao sortResultDao;

    @Override
    public SortedResult create( final int amount ) {

        List<Integer> numbers = generateRandomNumbers( amount );
        List<Integer> sortedNumbers = new ArrayList<Integer>();

        sortedNumbers.addAll( numbers );

        long sortStarted = System.currentTimeMillis();

        final int[] changes = { 0 };

        // uses merge sort - slow
        Collections.sort( sortedNumbers, new Comparator<Integer>(){
            @Override
            public int compare( Integer arg0, Integer arg1 ) {
                changes[ 0 ] += 1;
                return arg0.compareTo( arg1 );
            }
        } );

        long millis = System.currentTimeMillis() - sortStarted;

        SortedResult result = new SortedResult( numbers.toString(), sortedNumbers.toString(),
                changes[ 0 ], millis );

        try {
            result = sortResultDao.save( result );
        }
        catch( final Exception exception ) {
            result = null;
        }

        return result;
    }

    public List<Integer> generateRandomNumbers( final int amount ) {

        List<Integer> numbers = new ArrayList<Integer>();

        Random rand = new Random();

        for( int i = 0; i < amount; i++ ) {
            numbers.add( rand.nextInt( 100 ) + 1 );
        }

        return numbers;
    }

}
