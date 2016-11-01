package controllers;

import annotations.AControllerMethod;
import discovery.DiscoverActions;
import discovery.DiscoverAttributes;
import discovery.DiscoverModels;
import discovery.DiscoverUsecases;
import interfaces.IController;
import interfaces.IDiscovery;
import jresponseclasses.JDiscovery;
import jresponseclasses.JReturn;
import model.Describe;
import model.Model;
import reflection.GenericReflection;
import reflection.ReflectionController;
import utils.StringUtils;

public class DiscoveryController extends GenericController {
	
	public DiscoveryController(){
	}
	
	@AControllerMethod(checkAttributes = false, needAuthentication = false)
	public JReturn discovermodelsAction(JReturn r, Model object){
		DiscoverModels dm = new DiscoverModels();
		this.setDiscovery(r, dm);
		
		return r;
	}
	
	@AControllerMethod(checkAttributes = false, needAuthentication = false)
	public JReturn discoveractionsAction(JReturn r, Model object){
		Describe describe = (Describe)object;
		IController ic = null;
		try{
			String classname = "controllers."+StringUtils.setFirstLetterUppercase(describe.dgetName()+"Controller");
			ic = (IController) ReflectionController.instanciateObjectByName(classname);
		}catch(RuntimeException re){
			r.addSimpleError(re.getMessage());
		}
		
		DiscoverActions da = new DiscoverActions(ic);
		da.setDiscoveryObject(ic);
		
		JDiscovery jd = setDiscovery(r, da);
		jd.setDicoveryObjectName(describe.dgetName());
		
		return r;
	}


	
	@AControllerMethod(checkAttributes = false, needAuthentication = false)
	public  JReturn discoverusecasesAction(JReturn r, Model object){
		DiscoverUsecases du = new DiscoverUsecases();
		
		setDiscovery(r, du);
		
		return r;
		
	}
	
	@AControllerMethod(checkAttributes = false, needAuthentication = false)
	public  JReturn discoverattributesAction(JReturn r, Model object){
		Describe describe = (Describe)object;
		Model model = null;
		try{
			model = (Model) GenericReflection.instanciateObjectByName("model."+describe.dgetName());
		}catch(RuntimeException re){
			r.addSimpleError(re.getMessage());
		}
		
		DiscoverAttributes da = new DiscoverAttributes();
		da.setDiscoveryObject(model);
		
		JDiscovery jd =this.setDiscovery(r, da);
		jd.setDicoveryObjectName(describe.dgetName());
		
		return r;
	}


	private JDiscovery setDiscovery(JReturn r, IDiscovery<?> discovery) {
		JDiscovery jd = null;
		
		try{
			jd = new JDiscovery(discovery);
		}catch(Exception e){
			r.addSimpleError(e.getMessage());
		}
		
		if(r.isSuccess()){
			r.setDiscover(jd);
		}
		
		return jd;
	}
	
	
	
}
