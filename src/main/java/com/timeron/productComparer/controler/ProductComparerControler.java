package com.timeron.productComparer.controler;

import java.util.List;

import org.apache.log4j.Logger;

import com.timeron.MultiObserver.dao.ObservedObjectDAO;
import com.timeron.MultiObserver.dao.ObservedSiteDAO;
import com.timeron.MultiObserver.dao.entity.ObservedSite;
import com.timeron.MultiObserver.dao.entity.Site;
import com.timeron.productComparer.util.Comparer;

public class ProductComparerControler {
	private static final Logger LOG = Logger.getLogger(ProductComparerControler.class);
	
	
	
	public void runProductComparerControler() {
		Comparer comparer = new Comparer();
		ObservedSiteDAO observedSiteDAO = new ObservedSiteDAO();
		ObservedObjectDAO observedObjectDAO = new ObservedObjectDAO();
		
		List<ObservedSite> observedSites = observedSiteDAO.getSitesWithoutProductKay();
		if (observedSites!=null) {
			for (ObservedSite observedSite : observedSites) {
				getProductKay(observedSite);
			}
		}else{
			LOG.info("Wszystkie produkty mają klucz");
		}
		
		comparer.findProductKayInName(observedSiteDAO.getSitesWithoutProductKay(), observedObjectDAO.getAll());

	}

	private void getProductKay(ObservedSite observedSite) {
		
		Site site = observedSite.getObservedLinksPackage().getSite();

		if(site.getProductKayXPath() == null || site.getProductKayXPath().isEmpty()){
			LOG.warn("site nie ma productKeyXPath");
		}else{
			ProductKayControler productKayControler = new ProductKayControler(observedSite);

			productKayControler.setProductKayFromSite();
			productKayControler.saveProductKay();
		}
		
		
	}

}
