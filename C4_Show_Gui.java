package CFour;

public class C4_Show_Gui {

	public static void main(String[] args) {
			javax.swing.SwingUtilities.invokeLater(new Runnable(){
				@Override
			public void run(){
				ConnectFour gui = new ConnectFour();
				}
			});
		}
	}
