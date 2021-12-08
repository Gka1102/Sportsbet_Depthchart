package com.sports.depthchart.model;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;

@Component
public class SportsPositionReference{
    /**
     * This is a static reference info for sports & positions linked to each sport for future purpose.
     */
     public enum SportsEnum{
         NFL, MLB
    }

    public EnumMap getSportPosMap()

    {
        Set nflPosSet = new HashSet();
        nflPosSet.add("QB");
        nflPosSet.add("WR");
        nflPosSet.add("RB");
        nflPosSet.add("P");
        nflPosSet.add("TE");
        nflPosSet.add("K");
        nflPosSet.add("KR");
        nflPosSet.add("PR");

        Set mlbPositionSet = new HashSet();
        mlbPositionSet.add("SP");
        mlbPositionSet.add("RP");
        mlbPositionSet.add("C");
        mlbPositionSet.add("1B");
        mlbPositionSet.add("2B");
        mlbPositionSet.add("SS");
        mlbPositionSet.add("LF");
        mlbPositionSet.add("RF");
        mlbPositionSet.add("CF");
        mlbPositionSet.add("DH");


        EnumMap<SportsPositionReference.SportsEnum, Set> sportPosMap = new EnumMap<SportsEnum, Set>(SportsPositionReference.SportsEnum.class);
        sportPosMap.put(SportsEnum.NFL, nflPosSet);
        sportPosMap.put(SportsEnum.MLB, mlbPositionSet);
        return sportPosMap;
    }

}
