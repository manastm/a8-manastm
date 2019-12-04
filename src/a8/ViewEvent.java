package a8;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ViewEvent {
	//have an event for slider, check, button, etc
	
	Object obj;
	JComponent jc;
	
	boolean isUpdate = false;
	boolean isRandom = false;
	boolean isClear = false;
	boolean isTorus = false;
	boolean isChangeSize = false;
	boolean isSpeed = false;
	boolean isStart = false;
	int speed;
	
	public ViewEvent(ActionEvent e) {
		obj = e.getSource();
		if (obj instanceof JComponent) {
			jc = (JComponent) obj;
			if (jc.getName().equals("update")) {
				isUpdate = true;
			}
			else if (jc.getName().equals("random")) {
				isRandom = true;
			}
			else if (jc.getName().equals("clear")) {
				isClear = true;
			}
			else if (jc.getName().equals("torus")) {
				isTorus = true;
			}
			else if (jc.getName().equals("size")) {
				isChangeSize = true;
			}
			else if (jc.getName().equals("start")) {
				isStart = true;
			}
			else if (jc.getName().equals("stop")) {
				isStart = false;
			}
		}
	}
	
	public ViewEvent(ChangeEvent e) {
		obj = e.getSource();
		if (obj instanceof JComponent) {
			jc = (JComponent) obj;
			if (jc.getName().equals("speed")) {
				isSpeed= true;
				speed = ((JSlider) jc).getValue();
			}
		}
	}
	
	public boolean getIsUpdate() {
		return isUpdate;
	}
	
	public boolean getIsRandom() {
		return isRandom;
	}
	
	public boolean getIsClear() {
		return isClear;
	}
	
	public int getSpeed() {
		return speed;
	}
	
}
