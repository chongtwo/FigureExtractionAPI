package com.springboot.two;

import com.springboot.two.service.FigureExtraction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwoApplicationTests {

	@Test
	public void contextLoads() {
		String aLongSentence="肝脏大小、形态及各叶比例正常，边缘光滑，肝裂不宽。肝右叶可见一41*51mm低密度灶，" +
				"边界尚清晰，动脉期CT值34HU，静脉期强化不明显。肝内外胆管无扩张。胆囊不大，囊内未见异常密度灶，" +
				"囊壁不厚。胃扩张不满意。脾脏大小、形态、密度未见异常。所扫层面左肾未见显示。";//原方式传的参数
		FigureExtraction figureExtraction = new FigureExtraction();
		ArrayList<String> longSentenceList = new ArrayList<String>();
		longSentenceList.add(aLongSentence);
		ArrayList<ArrayList<String>> allList = null;
		try {
			allList = figureExtraction.go(longSentenceList);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.printf(allList.toString());
	}

}
