package com.itibo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@ManagedBean(name="language")
@SessionScoped
public class LanguageBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String localeCode = "en_EN";
	
	private static Map<String,Object> countries;
	static{
	//	Locale.setDefault(new Locale("ru","RU"));
		countries = new LinkedHashMap<String,Object>();
		countries.put("Русскй", new Locale("ru","RU")); //label, value
		countries.put("English", new Locale("en","EN"));
	}

	public Map<String, Object> getCountriesInMap() {
		return countries;
	}

	
	public String getLocaleCode() {
		return localeCode;
	}


	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}


	public void countryLocaleCodeChanged(ValueChangeEvent e){
		String newLocaleValue = e.getNewValue().toString();
		//loop a map to compare the locale code
        for (Map.Entry<String, Object> entry : countries.entrySet()) {
        	if(entry.getValue().toString().equals(newLocaleValue)){
        		FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale)entry.getValue());
        	}
        }

	}

}