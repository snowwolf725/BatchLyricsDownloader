package org.batchexecutor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.JButton;


public class DownloadListener implements ActionListener {
	Timer timer = null;
	JButton _btnDownloadLyrics = null;
	DownloadTask _task = null;
	int _delaytime = 0;
	
	DownloadListener(JButton btnDownloadLyrics,DownloadTask task, int delaytime){
		_btnDownloadLyrics = btnDownloadLyrics;
		_delaytime = delaytime;
		_task = task;
		timer = new Timer();
		timer.schedule(_task, 0, _delaytime);
		_task.setEnable(false);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		try{
			if(_task.isEnable() == false)
			{
				_btnDownloadLyrics.setText("Stop");
				_task.setEnable(true);
			} else{
				_task.setEnable(false);
				_btnDownloadLyrics.setText("Start");
			}
		}catch(Exception e){
			System.out.println("\n Hello");
			e.printStackTrace();
		}
	}
}
