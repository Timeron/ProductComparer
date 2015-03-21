package com.timeron.productComparer.controler;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.timeron.MultiObserver.downloader.Downloader;
import com.timeron.NexusDatabaseLibrary.Entity.ObservedObject;
import com.timeron.NexusDatabaseLibrary.Entity.ObservedSite;
import com.timeron.NexusDatabaseLibrary.Entity.Site;
import com.timeron.NexusDatabaseLibrary.dao.ObservedObjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.ObservedSiteDAO;

public class ProductKayControler {

	static Logger LOG = Logger.getLogger(ObservedSiteDAO.class.getName());

	String productKay = "";
	ObservedSite observedSite;

	public ProductKayControler(ObservedSite observedSite) {
		this.observedSite = observedSite;
	}

	public String getProductKay() {
		return productKay;
	}

	public void setProductKay(String productKay) {
		this.productKay = productKay;
	}

	public void setProductKayFromSite() {
		Downloader downloader = new Downloader();
		HtmlPage htmlPage = downloader.getSite(observedSite.getUrl());
		try {
			String productKayTemp = productKayFromhtmlPage(observedSite
				.getObservedLinksPackage().getSite(), htmlPage);
			this.productKay = parseProductKay(productKayTemp);
		}catch(NullPointerException ex){
			LOG.info("Strona wygasła");
		}
		
	}

	@SuppressWarnings("unchecked")
	private String productKayFromhtmlPage(Site site, HtmlPage htmlPage)
			throws NullPointerException {
		List<DomNode> productKayNode = (List<DomNode>) htmlPage.getByXPath(site
				.getProductKayXPath());
		if (!productKayNode.isEmpty()) {
			productKay = productKayNode.get(0).getTextContent();
			return productKay;
		} else {
			return null;
		}

	}

	public void saveProductKay() {
		ObservedObject observedObject = new ObservedObject();
		ObservedObjectDAO observedObjectDAO = new ObservedObjectDAO();
		ObservedSiteDAO observedSiteDAO = new ObservedSiteDAO();

		observedObject.setName(observedSite.getArticleName());
		observedObject.setProductKay(productKay);
		observedObject.setTimestamp(new Date());

		observedObjectDAO.save(observedObject);
		LOG.info(observedObject.getId());

		observedSite.setObservedObject(observedObject);
		observedSite.setApprovedProductKay(true);
		observedSiteDAO.update(observedSite);
	}

	private String parseProductKay(String productKay) {
		String tempKey = "";
		switch (observedSite.getObservedLinksPackage().getSite().getName()) {
		case "Komputronik":
			tempKey = productKay.substring(productKay.indexOf('[') + 1,
					productKay.indexOf(']'));
			productKay = tempKey;
			break;
		}

		return productKay;
	}
}
