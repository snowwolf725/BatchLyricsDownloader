package org.batchexecutor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

/**
 * 
 */

/**
 * @author SnowWolf725
 *
 */
public class BatchExecutor {
	final static  JFrame m_frm = new JFrame("Batch Executor");
	private static  JTextField _txt_playerLocation;
	private static  JTextField _txt_delayTime;
	public static JLabel _lrcCount = new JLabel("0");
	public static DefaultListModel _model_songList = new DefaultListModel();
	public static JList _list_songList = new JList(_model_songList);
	public static JScrollPane listScroller;
	private static JCheckBox chckbxGetSongLyrics = new JCheckBox("Get song Lyrics");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		m_frm.getContentPane().setLayout(null);
		
		_txt_playerLocation = new JTextField();
		_txt_playerLocation.setText("C:\\Program Files\\TTPlayer\\TTPlayer.exe");
		_txt_playerLocation.setBounds(14, 48, 396, 21);
		m_frm.getContentPane().add(_txt_playerLocation);
		_txt_playerLocation.setColumns(10);
		
		JButton btnSelectPlayerLocation = new JButton("Application Location");
		btnSelectPlayerLocation.addActionListener(new ActionListener() {
			private File file;
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				fc.setCurrentDirectory(file);
				int returnVal = fc.showOpenDialog(m_frm);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
					if(file.isFile() && Utils.getExtension(file) != null &&
							Utils.getExtension(file).equals("exe"))
						_txt_playerLocation.setText(file.getPath());
	            } else {
	            
	            }
			}
		});
		btnSelectPlayerLocation.setBounds(424, 47, 152, 23);
		m_frm.getContentPane().add(btnSelectPlayerLocation);
		
		listScroller = new JScrollPane(_list_songList);
		listScroller.setPreferredSize(new Dimension(250, 80));
		listScroller.setBounds(14, 108, 396, 142);
		_list_songList.setLayoutOrientation(JList.VERTICAL);
		_list_songList.setValueIsAdjusting(true);
		_list_songList.setBounds(14, 108, 396, 142);
		//m_frm.getContentPane().add(_list_songList);
		m_frm.getContentPane().add(listScroller);
		
		JButton btnSongsList = new JButton("Add Songs ");
		btnSongsList.addActionListener(new ActionListener() {
			
			private File file;
			
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setCurrentDirectory(file);
				int returnVal = fc.showOpenDialog(m_frm);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					for(File selfile:fc.getSelectedFiles()){
						file = selfile;
						if(file.isDirectory())
							_model_songList.addElement(file.getPath());
					}
	            }
			}
		});
		btnSongsList.setBounds(424, 105, 152, 23);
		m_frm.getContentPane().add(btnSongsList);
		
		_txt_delayTime = new JTextField();
		_txt_delayTime.setText("25");
		_txt_delayTime.setBounds(14, 12, 396, 21);
		m_frm.getContentPane().add(_txt_delayTime);
		_txt_delayTime.setColumns(10);
		
		
		JButton btnAddSingleSongs = new JButton("Add Song Files");
		btnAddSingleSongs.addActionListener(new ActionListener() {
			
			private File file = null;
			
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				fc.setCurrentDirectory(file);
				int returnVal = fc.showOpenDialog(m_frm);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					for(File selfile:fc.getSelectedFiles()){
						file = selfile;
						_model_songList.addElement(file.getPath());
					}
	            }
			}
		});
		btnAddSingleSongs.setBounds(424, 137, 152, 23);
		m_frm.getContentPane().add(btnAddSingleSongs);
		
		JButton btnDownloadLyrics = new JButton("Start");
		DownloadTask task = new DownloadTask(_lrcCount, _model_songList, _list_songList,_txt_playerLocation, chckbxGetSongLyrics);
		int _delaytime = Integer.parseInt(_txt_delayTime.getText());
		DownloadListener dlistener = new DownloadListener(btnDownloadLyrics, task, _delaytime);
		btnDownloadLyrics.addActionListener(dlistener);
		btnDownloadLyrics.setBounds(424, 11, 152, 23);
		m_frm.getContentPane().add(btnDownloadLyrics);
		
		_lrcCount.setBounds(139, 79, 26, 15);
		m_frm.getContentPane().add(_lrcCount);
		
		JLabel lblLrc = new JLabel("Downloaded LRC Files");
		lblLrc.setBounds(14, 81, 141, 15);
		m_frm.getContentPane().add(lblLrc);
		
		JButton btnDelSongs = new JButton("Del Songs");
		btnDelSongs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(Object obj : _list_songList.getSelectedValues()){
					_model_songList.removeElement(obj);
				}
				_list_songList.updateUI();
			}
		});
		btnDelSongs.setBounds(424, 170, 152, 23);
		m_frm.getContentPane().add(btnDelSongs);
		
		
		chckbxGetSongLyrics.setBounds(311, 75, 99, 23);
		m_frm.getContentPane().add(chckbxGetSongLyrics);
		m_frm.setTitle("Batch Executor");
		m_frm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		m_frm.setVisible(true);
		m_frm.setBounds(0, 0, 600, 600);
	}
}
