package com.nttdata.bootcamp.passive.util;

import java.math.BigDecimal;

/**
 * Constantes.
 *
 */
public class Constantes {	
	
	public static final String DEPOSITO = "1";
	public static final String RETIRO = "-1";
	public static final String MAXIMO_MOVIMIENTO = "20";
	public static final String TYPE_RUC = "RUC";
	public static final String TYPE_DNI = "DNI";
	public static final String TYPE_ACCOUNT_AHORRO= "AHORRO";
	public static final String TYPE_ACCOUNT_CORRIENTE= "CORRIENTE";
	public static final String TYPE_ACCOUNT_PLAZO_FIJO= "PLAZOFIJO";
	public static final String CREATE_ACCOUNT_AHORRO= "CUENTA DE AHORRO";
	public static final String CREATE_ACCOUNT_CORRIENTE= "CUENTA DE CORRIENTE";
	public static final String CREATE_ACCOUNT_PLAZO_FIJO= "CUENTA PLAZO FIJO";
	public static final BigDecimal MAX_MOVIMIENTO= new BigDecimal(30) ;
	public static final BigDecimal MIN_MOVIMIENTO= new BigDecimal(1) ;
	public static final BigDecimal MANTENIMIENTO= new BigDecimal(8);
	
	private Constantes() {
		
	}
}
