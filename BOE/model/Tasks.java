package BOE.model;

import java.time.LocalDate;
import java.util.*;

import javafx.beans.property.ObjectProperty;

/**
 * 
 */
public class Tasks {

  /**
   * Default constructor
   */
  public Tasks() {
  }

  /**
   * 
   */
 // private final ObjectProperty<LocalDate>  PoP_Start;
  
 // private final ObjectProperty<LocalDate>  PoP_Stop;
  /**
   * 
   */
  public String Name;

  /**
   * 
   */
  public String Basis_of_Estimate_Formula;

  /**
   * 
   */
  public float Staff_Hours;

  /**
   * 
   */
  public String Estimate_Methodology_and_Ratiional;

  private SOW_Ref[] sow;

  private cond_assumption[] conditions; 
}