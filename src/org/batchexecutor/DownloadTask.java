package org.batchexecutor;
import java.io.File;
import java.util.TimerTask;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import org.batchexecutor.lyric.Lyric;
import org.batchexecutor.playlist.PlayListItem;
import org.batchexecutor.util.Util;


/**
 * 
 */

/**
 * @author SnowWolf725
 *
 */
public class DownloadTask extends TimerTask {
	
	JLabel _lrcCount;
	DefaultListModel _model_songList;
	JList _list_songList;
	JTextField _txt_playerLocation;
	boolean isEnable;
	JCheckBox _chckbxGetSongLyrics;
	
	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	DownloadTask(JLabel lrcCount,
			DefaultListModel model_songList,
			JList list_songList,
			JTextField txt_playerLocation,
			JCheckBox chckbxGetSongLyrics){
		_lrcCount = lrcCount;
		_model_songList = model_songList;
		_list_songList = list_songList;
		_txt_playerLocation = txt_playerLocation;
		_chckbxGetSongLyrics = chckbxGetSongLyrics;
		isEnable = true;
	}

	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run() {
		if(isEnable == false)
			return;
		boolean isProcessOneFile = false;
		while(_model_songList.getSize()>0 && isProcessOneFile == false){
			try {
					String cmd = _txt_playerLocation.getText() + " \"" ;
					File file = new File(_model_songList.getElementAt(0).toString());
					System.out.println("[process]"+file.getPath()+","+_model_songList.getElementAt(0).toString());
					if(file.getPath().equals(_model_songList.getElementAt(0).toString()) == false){
						_model_songList.remove(0);
						continue;
					}
					_model_songList.remove(0);
					if(file.exists() == false)
						continue;
					if(file.isFile() && Utils.getExtension(file) != null &&
							Utils.getExtension(file).equals("mp3")){
						cmd += file.getPath() + "\"";
						String _str_lrcpath = file.getPath();
						_str_lrcpath = _str_lrcpath.substring(0, _str_lrcpath.length() - 3);
						_str_lrcpath += "lrc";
						File _lrcfile = new File(_str_lrcpath);
						if(_lrcfile.exists())
							continue;
						
						try {
							
							if(_chckbxGetSongLyrics.isSelected()) {
								PlayListItem item = new PlayListItem(Util.getSongName(file), file.getPath(), -1, true);
								Lyric lrc = new Lyric(item);
								
								_lrcfile = new File(_str_lrcpath);
								if(_lrcfile.exists()){
									int fileCount = Integer.parseInt(_lrcCount.getText()) + 1;
									_lrcCount.setText(fileCount + "");
								}
								
								Thread.sleep(10000);
							} else {
								Thread.sleep(5000);
								Runtime r = Runtime.getRuntime();
								Process p = r.exec(cmd);
								Thread.sleep(20000);
								p.getErrorStream();
								p.destroy();
							}
							isProcessOneFile = true;
						//} catch (IOException e) {
							// TODO Auto-generated catch block
						//	e.printStackTrace();
						} catch(ArrayIndexOutOfBoundsException e){
							e.printStackTrace();
						}
					}
					else if(file.isDirectory()){
						for(File subfile:file.listFiles()){
							if(!subfile.equals(file))
							{
								System.out.println("[add]"+subfile.getPath());
								_model_songList.insertElementAt(subfile.getPath(), 0);
							}
						}
						continue;
					}
					_list_songList.updateUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	}

}
