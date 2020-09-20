package elagin.dmitry.tasktrackingsystem.controller;




/**
 *@author Dmitry Elagin
 *Base class for controllers used in CustomDialog
 * @param <T> class of  object that will be used as the controller data model
 */
public abstract class DialogController<T> {

    protected T model;

    protected boolean result;

    /**
     * Sets an object as a controller model
     * @param model an object to be used as a controller model
     */
    public void setModel(T model) {
        this.model=model;
    }

    /**
     * returns a result depending on a user decision
     * @return true - if an dialog is closed with a positive action, false - otherwise
     */
    public boolean getResult() {
        return result;
    }

    /**
     * event handler for positive action
     */
   abstract void onSaveAction();

    /**
     * event handler for cancel action
     */
  abstract void onCancelAction();




}
