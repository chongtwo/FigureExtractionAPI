package com.springboot.two.service;

import com.springboot.two.domain.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        FigureExtraction figureExtraction = new FigureExtraction();
//        ArrayList<String> longSentenceList = TxtOperator.readTxt(".\\static\\CT胸部平扫约4000份-描述.txt");
        ArrayList<String> longSentenceList = TxtOperator.readTxt(".\\out\\distinctSentence2018-03-28-14-37-41.txt");
//        ArrayList<String> longSentenceList = new ArrayList<>();longSentenceList.add("左肺下叶可见多发结节状高密度影，");
        figureExtraction.go(longSentenceList);
        long endTime = System.currentTimeMillis();
        System.out.println("用时:"+ (endTime-startTime) + "ms");
    }


    public ArrayList<ArrayList<String>> go(ArrayList<String> longSentenceList) {
        int numOfLong = 0;
        ArrayList<ArrayList<String>> allList = new ArrayList<>();
//        XlsOperator xlsOperator = new XlsOperator();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//        String excelPath = "./out/result"+ String.valueOf(dateFormat.format(new Date())) + ".xlsx";
        String excelPath = "./out/distinctresult"+ String.valueOf(dateFormat.format(new Date())) + ".xlsx";
        //写入表头
        ArrayList<String> columnName = new ArrayList<>();
        columnName.add("原句");
        columnName.add("语义");
        columnName.add("主干部位");
        columnName.add("细节部位");
        columnName.add("区域");
        columnName.add("可能性");
        columnName.add("性状");
        columnName.add("诊断");
        columnName.add("变化");
        columnName.add("数量");
        columnName.add("测量位置");
        columnName.add("测量值");
        columnName.add("单位");
//        xlsOperator.writeXls(excelPath, columnName);

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
                    columnContent.add(se.primaryLocation);
                    columnContent.add(se.specificLocation);
                    columnContent.add(se.region);
                    columnContent.add(se.possibility);
                    columnContent.add(se.descriptor);
                    columnContent.add(se.diagnosis);
                    columnContent.add(se.change);
                    columnContent.add(se.quantifier);
                    columnContent.add(se.measureLocation);
                    columnContent.add(se.value);
                    columnContent.add(se.unit);

                    allList.add(columnContent);
                }
            }
        }
//        xlsOperator.writeXls(excelPath, allList);
        return allList;
    }
}
