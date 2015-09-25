package ufrj.cos.famelicus.servidor.engine;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import ufrj.cos.famelicus.servidor.engine.DeterminadorDeSituacao;

/**
 *
 * @author cyrilledelabre
 */
public class MyTimer {
	private static MyTimer INSTANCE = null;
	
	DeterminadorDeSituacao determinador;
    private Timer startTimer;
    private Timer stopTimer;
    private Timer determinadorTimer;
	private String ultimaActualizacao;
	private boolean openingHours;
	private Calendar opening; 
	private Calendar closing;
	private static final long PERIOD = 5000;//86400000; //ms = 24h
    private long votingTimeInterval;
   
    //singleton design pattern....
    public static MyTimer getInstance(DeterminadorDeSituacao determinador, long votingTimeInterval, int openingHour, int closingHour) {
		if (INSTANCE == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (MyTimer.class) {
				if (INSTANCE == null) {
					INSTANCE = new MyTimer(determinador, votingTimeInterval, openingHour, closingHour);
				}
			}
		} 
		return INSTANCE;
    }


    private MyTimer(DeterminadorDeSituacao determinador, long votingTimeInterval, int openingHour, int closingHour){
    	this.openingHours = false;
        this.determinador = determinador; 
        this.ultimaActualizacao = MyTimer.getCurrentTime();
        initTimers(closingHour, closingHour, votingTimeInterval);
    }
    
    private void initTimers(int openingHour,int closingHour, long votingTimeInterval){        
    	this.votingTimeInterval = votingTimeInterval;    	
    	
    	opening = Calendar.getInstance();
    	closing = Calendar.getInstance();
    	
    	setOpeningHour(openingHour);
    	setClosingHour(closingHour);
    	setVotingTimeInterval(votingTimeInterval);
    }
        
    public void setOpeningHour(int openingHour){ 
    	if (this.startTimer != null ) {
    		this.startTimer.cancel();
    		this.startTimer = null;
    	}
    	this.startTimer = new Timer();
    	
    	opening.set( Calendar.DAY_OF_WEEK, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
    	opening.set(Calendar.HOUR_OF_DAY, openingHour);
    	opening.set(Calendar.MINUTE, 0);
    	opening.set(Calendar.SECOND, 0);
    	opening.set(Calendar.MILLISECOND, 0);
    	
    	startTimer.scheduleAtFixedRate(new StartServerTark(), opening.getTime(), PERIOD);
    	setVotingTimeInterval(votingTimeInterval);
	}
    
    public void setClosingHour(int closingHour){
    	if (this.stopTimer != null ) {
    		this.stopTimer.cancel();
    		this.stopTimer = null;
    	}
    	this.stopTimer = new Timer();
    	
    	closing.set(Calendar.DAY_OF_WEEK, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
    	closing.set(Calendar.HOUR_OF_DAY, closingHour);
    	closing.set(Calendar.MINUTE, 0);
    	closing.set(Calendar.SECOND, 0);
    	closing.set(Calendar.MILLISECOND, 0);
    	
    	closing.set(Calendar.HOUR_OF_DAY, closingHour);
    	stopTimer.scheduleAtFixedRate(new StopServerTask(), closing.getTime(), PERIOD);
    }
    
    public  void setVotingTimeInterval(long votingTimeInterval){
    	if (this.determinadorTimer != null ) {
    		this.determinadorTimer.cancel();
    		this.determinadorTimer = null;
    	}
    	this.determinadorTimer = new Timer();
    	
    	this.votingTimeInterval = votingTimeInterval;
    	System.out.println("VOTING TIME = " + votingTimeInterval);
    	determinadorTimer.scheduleAtFixedRate(new StartDeterminadorTask(), votingTimeInterval, votingTimeInterval);
    }
    
    
    
    public String getUltimaActualizacao(){return this.ultimaActualizacao;}
    public boolean openingHours(){return openingHours;}
    
    public static String getCurrentTime(){return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());}

    private class StartDeterminadorTask extends TimerTask {
    	public void run(){
    		if(openingHours){
    			System.out.println("Executando votacao...");
    			determinador.determinar();
    			ultimaActualizacao = MyTimer.getCurrentTime(); 
    		}
    	}
    }
    
    private class StartServerTark extends TimerTask{
    	public void run(){
    		System.out.println("Abrindo servidor...");
    		openingHours = true;
    		
	        ultimaActualizacao = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
			determinador.reset();
			determinadorTimer = new Timer();
			determinadorTimer.scheduleAtFixedRate(new StartDeterminadorTask(), votingTimeInterval, votingTimeInterval);
    	}
    }
    
    private class StopServerTask extends TimerTask{
		public void run(){
			System.out.println("Fechando servidor...");
			openingHours = false;
			determinadorTimer.cancel();//stop the determinador do situacao
			determinadorTimer = null;
			stopTimer.cancel();
		}
    }

    
}
