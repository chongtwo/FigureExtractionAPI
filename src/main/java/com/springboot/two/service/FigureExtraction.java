package com.springboot.two.service;

import com.springboot.two.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@Component

public class FigureExtraction {

    public ArrayList<ArrayList<String>> go(ArrayList<String> longSentenceList) {
        int numOfLong = 0;
        ArrayList<ArrayList<String>> allList = new ArrayList<>();

        //取一句长句，循环
        int end = longSentenceList.size();
        for (String longS : longSentenceList.subList(0, end)) {
            numOfLong++;
            int numOfShort = 0;
            System.out.println(String.valueOf("长句编号：" + numOfLong));
            LongSentence longSentence = new LongSentence(longS);
            longSentence.segToShort();
            HashMap<Integer, StructuredShortSentence> numMap;
            for (ShortSentence ss : longSentence.shortSentences) {
                numOfShort++;
                System.out.println("短句编号：" + numOfShort);
                ss.match();
                ss.combineWord();
                numMap = RelationExtraction.relationExtract(ss.semanticSentence, ss.matchedDictionary);
                ArrayList<String> columnContent = null;

                for(Map.Entry<Integer, StructuredShortSentence> entry : numMap.entrySet()){
                    columnContent = new ArrayList<>();
                    columnContent.add(ss.content);
                    columnContent.add(ss.semanticSentence);

                    StructuredShortSentence se = entry.getValue();
                    columnContent.add("主干部位：" + se.primaryLocation);
                    columnContent.add("细节部位：" + se.specificLocation);
                    columnContent.add("区域：" + se.region);
                    columnContent.add("可能性：" + se.possibility);
                    columnContent.add("性状：" + se.descriptor);
                    columnContent.add("诊断：" + se.diagnosis);
                    columnContent.add("变化：" + se.change);
                    columnContent.add("数量：" + se.quantifier);
                    columnContent.add("测量部位：" + se.measureLocation);
                    columnContent.add("数值：" + se.value);
                    columnContent.add("单位：" + se.unit);

                    allList.add(columnContent);
                }
            }
        }
        return allList;
    }
}
