package ru.zaoemtika.EmtikaWork;

public abstract class RepaintProgressBar{
	public final static void repaintProgressBar(int step){
		AllWork.getProgressBar().setValue(AllWork.getProgressBar().getValue() + step);
		AllWork.getProgressBar().repaint();
	}
}
