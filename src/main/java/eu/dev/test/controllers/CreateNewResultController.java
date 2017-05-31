package eu.dev.test.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.dev.test.models.SortedResult;
import eu.dev.test.services.CreateNewResultService;

@Controller
public class CreateNewResultController {

    @Autowired
    private CreateNewResultService createNewResultService;

    @RequestMapping( value = "/api/createNewResult", method = RequestMethod.POST )
    public @ResponseBody Object createNewResult( @RequestBody int amount,
            HttpServletRequest request ) {

        Map<String, Object> map = new HashMap<String, Object>();

        map.put( "success", false );
        map.put( "message", "none" );

        if( amount <= 0 ) {
            map.put( "message", "Amount should be positive value and greater than zero!" );
        }
        else {
            SortedResult result = createNewResultService.create( amount );

            if( result == null ) {
                map.put( "message", "Error!" );
            }
            else {
                map.put( "success", true );
                map.put( "message", "SUCCESS" );
                map.put( "data", result );
            }
        }

        return map;
    }

}
