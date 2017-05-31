package eu.dev.test.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.dev.test.models.SortedResult;
import eu.dev.test.models.SortedResultDao;
import eu.dev.test.services.FindAllSortedResultsService;

@Service
public class FindAllSortedResultsServiceImpl implements FindAllSortedResultsService {

    @Autowired
    private SortedResultDao sortResultDao;

    @Override
    public List<SortedResult> find() {

        List<SortedResult> list = null;

        try {
            list = sortResultDao.findAllResults();
        }
        catch( final Exception exception ) {
            list = null;
        }

        return list == null ? new ArrayList<SortedResult>() : list;
    }

}
