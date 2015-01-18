package com.timeron.productComparer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.timeron.MultiObserver.stoper.Stoper;
import com.timeron.productComparer.controler.ProductComparerControler;

public class ProductComparer {
	private static final Logger LOG = Logger.getLogger(ProductComparer.class);

	public static DateFormat timerFormat = new SimpleDateFormat("HH:mm:ss");
	public static long start = System.currentTimeMillis();

	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		ProductComparerControler productComparerControler = new ProductComparerControler();

		LOG.info("#################################################");
		LOG.info("#                                               #");
		LOG.info("#   Nexus Product Comparer: " + dateFormat.format(start)
				+ " #");
		LOG.info("#                                               #");
		LOG.info("#################################################");
		
		productComparerControler.runProductComparerControler();
		long stop = System.currentTimeMillis();
		Stoper stoper = new Stoper(stop - start);
		LOG.info("Łączny czas: "+ (stoper.getTime()));
	}
}
