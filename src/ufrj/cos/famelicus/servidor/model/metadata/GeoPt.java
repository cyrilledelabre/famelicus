/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufrj.cos.famelicus.servidor.model.metadata;

/**
 *
 * @author cyrilledelabre
 */
public class GeoPt {
	//float int double ? actually depends on the device and the application
    private Float lat; 
    private Float lng; 
    
    public GeoPt(Float lat, Float lng){
    	set(lat, lng);
    }
    
    public Float getLat() { return lat;}
    public Float getLng(){return lng;}
  
    public void set(Float lat, Float lng){
        this.lat = lat; 
        this.lng = lng;
    }
}
