package com.github.sarxos.webcam;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * Proof of concept of how to handle webcam video stream from Java
 * 
 * @author Bartosz Firyn (SarXos)
 */
public class WebcamViewerExample extends JFrame implements Runnable, WebcamListener, WindowListener {

	private static final long serialVersionUID = -2831291292491395695L;

	private Webcam webcam = null;
	private WebcamPanel viewer = null;

	@Override
	public void run() {

		setTitle("Java Webcam Capture POC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(this);

		webcam = Webcam.getDefault();
		if (webcam == null) {
			System.out.println("No webcams found...");
			System.exit(1);
		}

		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.addWebcamListener(WebcamViewerExample.this);

		viewer = new WebcamPanel(webcam, false);

		setContentPane(viewer);
		pack();
		setVisible(true);

		Thread t = new Thread() {

			@Override
			public void run() {
				viewer.start();
			}
		};
		t.setDaemon(true);
		t.start();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new WebcamViewerExample());
	}

	@Override
	public void webcamOpen(WebcamEvent we) {
		System.out.println("webcam open");
	}

	@Override
	public void webcamClosed(WebcamEvent we) {
		System.out.println("webcam closed");
	}

	@Override
	public void webcamDisposed(WebcamEvent we) {
		System.out.println("webcam disposed");
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		webcam.close();
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		System.out.println("webcam viewer resumed");
		viewer.resume();
	}

	@Override
	public void windowIconified(WindowEvent e) {
		System.out.println("webcam viewer paused");
		viewer.pause();
	}

}
