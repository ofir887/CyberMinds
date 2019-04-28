package main.java;


import javax.ejb.Singleton;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


@Path("/")
@Singleton
public class Test {

    private ArrayList<Element> mElements;

    public Test() {
        mElements = new ArrayList<>();
    }

    @POST
    @Path("/test/add/")
    @Produces("application/json")
    public String putWord(@QueryParam("insert") String aWord) {
        Random random = new Random();
        int errorCode = random.nextInt(100);
        long wordValue = Helper.ASCIIWordSum(aWord);
        mElements.add(new Element(aWord, wordValue));
        Collections.sort(mElements, (element, t1) -> {
            if (element.getWordSum() > t1.getWordSum())
                return 1;
            else if (element.getWordSum() < t1.getWordSum()) return -1;
            else return 0;
        });
        String pattern =
                "{ \"Status\":\"200\",\"success\":\"%b\",\"parameter\":\"%s\" \"error\":\"%d\"}";
        return String.format(pattern, true, aWord, errorCode);
    }

    @GET
    @Path("/test/get/")
    @Produces("text/plain")
    public String getWord(@QueryParam("match") String aWord) {
        long asciValue = Helper.ASCIIWordSum(aWord);
        ArrayList<Element> nearestElements = new ArrayList<>();
        if (mElements.size() > 0) {
            nearestElements = Helper.getKclosest(mElements, asciValue, mElements.size() >= 3 ? 3 : mElements.size());
        }
        String pattern =
                "{ \"query\":\"%s\",\"top\":\"%s\"}";
        return String.format(pattern, aWord, nearestElements.toString());
    }

}