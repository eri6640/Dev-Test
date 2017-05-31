package eu.dev.test.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.dev.test.models.SortedResult;
import eu.dev.test.services.FindAllSortedResultsService;

@Controller
public class FindAllSortedResultsController {

    @Autowired
    private FindAllSortedResultsService findAllSortedResultsService;

    @RequestMapping( value = "/api/findAllSortedResults", method = RequestMethod.GET )
    public @ResponseBody Object findAllSortedResults( HttpServletRequest request ) {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put( "success", false );
        map.put( "message", "none" );

        List<SortedResult> list = findAllSortedResultsService.find();

        if( list.isEmpty() ) {
            map.put( "message", "No data!" );
        }
        else {
            map.put( "success", true );
            map.put( "message", "SUCCESS" );
            map.put( "data", list );
        }

        return map;
    }

}
