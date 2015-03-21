package com.timeron.productComparer.util;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.timeron.NexusDatabaseLibrary.Entity.ObservedObject;
import com.timeron.NexusDatabaseLibrary.Entity.ObservedSite;
import com.timeron.NexusDatabaseLibrary.Entity.ProposedProductKay;
import com.timeron.NexusDatabaseLibrary.dao.ProposedProductKayDAO;


public class Comparer {
	
	static Logger LOG = Logger.getLogger(Comparer.class.getName());
	
	private int minor = 5;
	private int small = 10;
	private int avarage = 25;
	private int medium = 50;
	private int big = 75;
	private int huge = 90;
	

	public void findProductKayInName(List<ObservedSite> observedSites, List<ObservedObject> observedObjects){
		ProposedProductKayDAO proposedProductKayDAO = new ProposedProductKayDAO();
		for(ObservedSite observedSite : observedSites){
			for(ObservedObject observedObject : observedObjects){
				if(observedSite.getArticleName().contains(observedObject.getProductKay())){
					observedSite.setObservedObject(observedObject);
					
					ProposedProductKay newProposedProductKay = new ProposedProductKay();
					
					List<ProposedProductKay> proposedProductKay = proposedProductKayDAO.getByObservedSiteAndObject(observedSite, observedObject);
					if(proposedProductKay.isEmpty()){
						newProposedProductKay.setObservedObject(observedObject);
						newProposedProductKay.setObservedSite(observedSite);
						newProposedProductKay.setAprovedProductKay(false);
						newProposedProductKay.setPercentCompatibility(9);
						newProposedProductKay.setTimestamp(new Date());
						proposedProductKayDAO.save(newProposedProductKay);
					}else{
						proposedProductKay.get(0).setPercentCompatibility(9);
						proposedProductKay.get(0).setLastUpdated(new Date());
						proposedProductKayDAO.update(proposedProductKay.get(0));
					}

					
					LOG.info("New propose: productKay - " +observedObject.getProductKay()+ " for product " +observedSite.getArticleName());
				}
			}
		}
	}
	
}
