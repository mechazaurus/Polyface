package com.ybene.polytech.polyface;

import android.content.Context;
import android.content.Entity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class Computation
{
    private FacialPoints reference;
    private FacialPoints actual;
    private Map<String, Integer> actionUnits;
    private Map<String, Double> emotions;
    private Context context;

    static int ActionUnits = 10;
    static int Emotions = 6;

    public Computation(Context Context)
    {
        actionUnits = new HashMap<>();
        for(int i=0; i<12; i++)
        {
            actionUnits.put("AU"+i, 0);
        }
        emotions = new HashMap<>();
        emotions.put("Hapiness", 0.0);
        emotions.put("Sadness", 0.0);
        emotions.put("Anger", 0.0);
        emotions.put("Surprise", 0.0);
        this.context = Context;
    }

    public void setReference(FacialPoints ref)
    {
        reference = ref;
    }

    public FacialPoints getReference() {
        return reference;
    }

    public FacialPoints getActual() {
        return actual;
    }

    public void setActual(FacialPoints actual) {
        this.actual = actual;
    }

    public List<Map.Entry<String, Double>> compute()
    {
        //TODO
        isAU0orAU1();
        isAU2orAU3();
        isAU4orAU5();
        isAU6orAU7();
        isAU8orAU9();
        isAU10();
        isAU11();
        getEmotions();
        Set<Map.Entry<String, Double>> set = emotions.entrySet();
        List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Double>>()
        {
            public int compare( Map.Entry<String, Double> o1, Map.Entry<String, Double> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );

        Log.i("Emotions", actionUnits.toString());
        Log.i("Emotions", list.toString());
        Log.i("Emotions", "");
        return list;
    }


    public void isAU0orAU1()
    {
        //Get referenceX1, referenceY1, referenceX2, referenceY2
        //Get midPoint
        Point ref1 = reference.getEyebrow3();
        Point ref2 = reference.getEyebrow4();
        Point midRef = reference.getMidPoint();

        //Get actualX1, actualY1, actualX2, actualY2
        //Get midPoint
        Point actual1 = actual.getEyebrow3();
        Point actual2 = actual.getEyebrow4();
        Point midActual = actual.getMidPoint();

        //Compare
        double referenceAverageY = (ref1.compareUpSideY(midRef) + ref2.compareUpSideY(midRef)) /2 ;
        double actualAverageY = (actual1.compareUpSideY(midActual) + actual2.compareUpSideY(midActual)) / 2;

        //IS ACTION UP
        if (actualAverageY> referenceAverageY)
        {
            if((actualAverageY-referenceAverageY)>35)
            {
                Log.i("AUs", "AU0 "+ String.valueOf(actualAverageY-referenceAverageY));
                actionUnits.put("AU0", 1);
            }
            else
            {
                Log.i("AUs", "AU0 Same");
                actionUnits.put("AU0", 0);
            }
        }
        //IS ACTION DOWN
        else if (actualAverageY < referenceAverageY)
        {
            if((referenceAverageY-actualAverageY)>10)
            {
                Log.i("AUs", "AU1 " + String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU1", 1);
            }
            else
            {
                Log.i("AUs", "AU1 Same");
                actionUnits.put("AU1", 0);
            }
        }
    }


    public void isAU2orAU3()
    {

        //Get referenceX1, referenceY1, referenceX2, referenceY2
        //Get midPoint
        Point ref1 = reference.getEyebrow1();
        Point ref2 = reference.getEyebrow2();
        Point midRef = reference.getMidPoint();

        //Get actualX1, actualY1, actualX2, actualY2
        //Get midPoint
        Point actual1 = actual.getEyebrow1();
        Point actual2 = actual.getEyebrow2();
        Point midActual = actual.getMidPoint();

        //Compare
        double referenceAverageY = (ref1.compareUpSideY(midRef) + ref2.compareUpSideY(midRef)) /2 ;
        double actualAverageY = (actual1.compareUpSideY(midActual) + actual2.compareUpSideY(midActual)) / 2;

        //IS ACTION UP
        if (actualAverageY> referenceAverageY)
        {
            if((actualAverageY-referenceAverageY)>10)
            {
                Log.i("AUs", "AU2 "+ String.valueOf(actualAverageY-referenceAverageY));
                actionUnits.put("AU2", 1);
            }
            else
            {
                Log.i("AUs", "AU2 Same" +String.valueOf(actualAverageY-referenceAverageY));
                actionUnits.put("AU2", 0);
            }
        }
        //IS ACTION DOWN
        else if (actualAverageY < referenceAverageY)
        {
            if((referenceAverageY-actualAverageY)>10)
            {
                Log.i("AUs", "AU3 " + String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU3", 1);
            }
            else
            {
                Log.i("AUs", "AU3 Same"+ String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU3", 0);
            }
        }

    }


    public void isAU4orAU5()
    {

        //Get referenceX1, referenceY1, referenceX2, referenceY2
        //Get midPoint
        Point ref1 = reference.getMouth1();
        Point ref2 = reference.getMouth2();
        Point midRef = reference.getMidPoint();

        //Get actualX1, actualY1, actualX2, actualY2
        //Get midPoint
        Point actual1 = actual.getMouth1();
        Point actual2 = actual.getMouth2();
        Point midActual = actual.getMidPoint();

        //Compare
        double referenceAverageY = (ref1.compareDownSideY(midRef) + ref2.compareDownSideY(midRef)) /2 ;
        double actualAverageY = (actual1.compareDownSideY(midActual) + actual2.compareDownSideY(midActual)) / 2;

        //IS ACTION UP
        if (actualAverageY> referenceAverageY)
        {
            if((actualAverageY-referenceAverageY)>5)
            {
                Log.i("AUs", "AU5 " + String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU5", 1);
            }
            else
            {
                Log.i("AUs", "AU5 Same"+ String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU5", 0);
            }
        }
        //IS ACTION DOWN
        else if (actualAverageY < referenceAverageY)
        {
            if((referenceAverageY-actualAverageY)>10)
            {
                Log.i("AUs", "AU4 "+ String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU4", 1);
            }
            else
            {
                Log.i("AUs", "AU4 Same" +String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU4", 0);
            }
        }
    }

    public void isAU6orAU7()
    {

        //Get referenceX1, referenceY1, referenceX2, referenceY2
        //Get midPoint
        Point ref1 = reference.getMouth3();
        Point midRef = reference.getMidPoint();

        //Get actualX1, actualY1, actualX2, actualY2
        //Get midPoint
        Point actual1 = actual.getMouth3();
        Point midActual = actual.getMidPoint();

        //Compare
        double referenceAverageY = ref1.compareDownSideY(midRef) ;
        double actualAverageY = actual1.compareDownSideY(midActual);

        //IS ACTION UP
        if (actualAverageY> referenceAverageY)
        {
            if((actualAverageY-referenceAverageY)>5)
            {
                Log.i("AUs", "AU7 " + String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU7", 1);
            }
            else
            {
                Log.i("AUs", "AU7 Same"+ String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU7", 0);
            }
        }
        //IS ACTION DOWN
        else if (actualAverageY < referenceAverageY)
        {
            if((referenceAverageY-actualAverageY)>10)
            {
                Log.i("AUs", "AU6 "+ String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU6", 1);
            }
            else
            {
                Log.i("AUs", "AU6 Same" +String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU6", 0);
            }
        }
    }

    public void isAU8orAU9()
    {

        //Get referenceX1, referenceY1, referenceX2, referenceY2
        //Get midPoint
        Point ref1 = reference.getMouth4();
        Point midRef = reference.getMidPoint();

        //Get actualX1, actualY1, actualX2, actualY2
        //Get midPoint
        Point actual1 = actual.getMouth4();
        Point midActual = actual.getMidPoint();

        //Compare
        double referenceAverageY = ref1.compareDownSideY(midRef) ;
        double actualAverageY = actual1.compareDownSideY(midActual);

        //IS ACTION UP
        if (actualAverageY> referenceAverageY)
        {
            if((actualAverageY-referenceAverageY)>5)
            {
                Log.i("AUs", "AU9 " + String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU9", 1);
            }
            else
            {
                Log.i("AUs", "AU9 Same"+ String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU9", 0);
            }
        }
        //IS ACTION DOWN
        else if (actualAverageY < referenceAverageY)
        {
            if((referenceAverageY-actualAverageY)>3)
            {
                Log.i("AUs", "AU8 "+ String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU8", 1);
            }
            else
            {
                Log.i("AUs", "AU8 Same" +String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU8", 0);
            }
        }
    }

    public void isAU10()
    {
        //Get referenceX1, referenceY1, referenceX2, referenceY2
        //Get midPoint
        Point ref1 = reference.getMouth3();
        Point ref2 = reference.getMouth4();

        //Get actualX1, actualY1, actualX2, actualY2
        //Get midPoint
        Point actual1 = actual.getMouth3();
        Point actual2 = actual.getMouth4();

        //Compare
        double referenceAverageY = ref2.getY() - ref1.getY() ;
        double actualAverageY = actual2.getY() - actual1.getY() ;

        //IS ACTION UP
        if (actualAverageY> referenceAverageY)
        {
            if(actualAverageY-referenceAverageY>35)
            {
                Log.i("AU10", "AU10 " + String.valueOf(actualAverageY-referenceAverageY));
                actionUnits.put("AU10", 1);
            }
            else
            {
                Log.i("AU10", "AU10 Same"+ String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU10", 0);
            }
        }
    }


    public void isAU11()
    {
        //Get referenceX1, referenceY1, referenceX2, referenceY2
        //Get midPoint
        Point ref1 = reference.getMouth4();
        Point ref2 = reference.getExtra();

        //Get actualX1, actualY1, actualX2, actualY2
        //Get midPoint
        Point actual1 = actual.getMouth4();
        Point actual2 = actual.getExtra();

        //Compare
        double referenceAverageY = ref1.getY() - ref2.getY() ;
        double actualAverageY = actual1.getY() - actual2.getY() ;

        //IS ACTION UP
        if (actualAverageY> referenceAverageY)
        {
            if(actualAverageY-referenceAverageY>15)
            {
                Log.i("AUs", "AU11 " + String.valueOf(actualAverageY-referenceAverageY));
                actionUnits.put("AU11", 1);
            }
            else
            {
                Log.i("AUs", "AU11 Same"+ String.valueOf(referenceAverageY-actualAverageY));
                actionUnits.put("AU11", 0);
            }
        }
    }

    public void getEmotions()
    {
        double score = 0;
        //TODO get emotions from AUs array
        //Happiness
        score = actionUnits.get("AU4") + actionUnits.get("AU11");
        score = score / 2;
        emotions.put("Hapiness", score);
        //Sadness
        score = actionUnits.get("AU5") + actionUnits.get("AU8");
        score = score / 2;
       // emotions.put("Sadness", score);
        //Anger
        score = actionUnits.get("AU1") + actionUnits.get("AU8") + actionUnits.get("AU10");
        score = score / 3;
        emotions.put("Anger", score);
        //Surprise
        score = actionUnits.get("AU0") + actionUnits.get("AU2") + actionUnits.get("AU9") + actionUnits.get("AU10");
        score = score / 4;
        emotions.put("Surprise", score);
    }


}
