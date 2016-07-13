//import statements
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


@SuppressWarnings("serial")
//class extending JApplet and implements interface ActionListener
public class AppletDijkstra extends JApplet implements ActionListener {
    //declaration of Class variables
	private JTextField tf_source;
	private JTextField tf_destination;
	private JTextArea ta_browse;
	private JTextArea ta_matrix;
	private JTextArea ta_rtable;
	private JTextArea ta_path;
	private JTextArea ta_distance;
	private JButton btn_generate;
	private JButton btn_browse;
	JLabel lbl_error;
	JPanel panel_matrix;
	Dijkstra dj;


	/**
	 * Create the applet.
	 */
    //Class Constructor for initialization
	public AppletDijkstra() {

        //creating a Frame and a container to add other elements to that frame
		JFrame frame = new JFrame("Link State Routing");
		Container c = (Container)frame.getContentPane();
        
        //Intializing/setting the Frame and the container with dimensions and Layouts
		setSize(new Dimension(500, 700));
		setPreferredSize(new Dimension(500, 700));
		c.setSize(new Dimension(600, 700));
		c.setPreferredSize(new Dimension(600, 700));
		c.setLayout(null);
        
        //creating Label,TextArea and Button for Accessing the topology file from the local directory
        
		JLabel lblFile = new JLabel("File");
		lblFile.setHorizontalAlignment(SwingConstants.CENTER);
		lblFile.setBounds(35, 16, 48, 22);
		c.add(lblFile);

		ta_browse = new JTextArea();
		ta_browse.setBorder(new LineBorder(new Color(0, 0, 0)));
		ta_browse.setBounds(84, 16, 233, 22);
		c.add(ta_browse);

		btn_browse = new JButton("Browse");
		btn_browse.setBounds(325, 16, 114, 23);
		c.add(btn_browse);
		ButtonBro bb = new ButtonBro();
		btn_browse.addActionListener(bb);  //creating an actionListener for giving a ButtonClick functionality

        //creating a label to display Errors that may occur when user inputs improper data
		lbl_error = new JLabel();

		lbl_error.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_error.setForeground(new Color(255,0,0));
		lbl_error.setBackground(UIManager.getColor("ToolBar.dockingForeground"));
		lbl_error.setBounds(84, 44, 355, 26);
		c.add(lbl_error);

        //creating a source and Destination Node textFields for user to input the Source and Destination Nodes
		JLabel lbl_source = new JLabel("Source Node");
		lbl_source.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_source.setBounds(45, 209, 114, 26);
		c.add(lbl_source);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(159, 102, 159, 85);
		frame.getContentPane().add(scrollPane_1);

        //creating a TextArea to display the Inputted matrix file from the local directory with Scrolls
		ta_matrix = new JTextArea();
		scrollPane_1.setViewportView(ta_matrix);
		ta_matrix.setEditable(false);
		

		JLabel lblMatrix = new JLabel("Matrix");
		lblMatrix.setHorizontalAlignment(SwingConstants.LEFT);
		lblMatrix.setBounds(206, 76, 92, 22);
		c.add(lblMatrix);

		JLabel lbl_destination = new JLabel("Destination Node");
		lbl_destination.setBounds(45, 256, 149, 26);
		c.add(lbl_destination);

		tf_source = new JTextField();
		tf_source.setBounds(205, 209, 124, 26);
		c.add(tf_source);
		tf_source.setColumns(10);

		tf_destination = new JTextField();
		tf_destination.setBounds(206, 256, 124, 26);
		c.add(tf_destination);
		tf_destination.setColumns(10);
        
        //Creating a Button Generate to implement the Protocol with the given topology matrix and Obtain the output
		btn_generate = new JButton("Generate");
		btn_generate.setMinimumSize(new Dimension(102, 35));
		btn_generate.setMaximumSize(new Dimension(102, 35));
		btn_generate.setPreferredSize(new Dimension(102, 30));
		btn_generate.setHorizontalTextPosition(SwingConstants.CENTER);
		btn_generate.setBounds(338, 233, 124, 25);
		c.add(btn_generate);
		btn_generate.addActionListener(this);
        
        //Creating Labels and TextAreas to output the Shortest Path and Optimal Distance
		JLabel lbl_path = new JLabel("Shortest Path");
		lbl_path.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_path.setBounds(59, 310, 146, 26);
		c.add(lbl_path);

		JLabel lbl_distance = new JLabel("Optimal Distance");
		lbl_distance.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_distance.setAutoscrolls(true);
		lbl_distance.setBounds(246, 310, 159, 26);
		c.add(lbl_distance);

		ta_path = new JTextArea();
		ta_path.setBorder(new LineBorder(new Color(0, 0, 0)));
		ta_path.setEditable(false);
		ta_path.setBounds(59, 357, 146, 22);
		c.add(ta_path);

		ta_distance = new JTextArea();
		ta_distance.setBorder(new LineBorder(new Color(0, 0, 0)));
		ta_distance.setEditable(false);
		ta_distance.setBounds(290, 357, 74, 22);
		c.add(ta_distance);

        //Creating Labels and TextArea to output the Routing Table obtained after executing the algorithm
		JLabel lbl_rtable1 = new JLabel("Source");
		lbl_rtable1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_rtable1.setBounds(45, 414, 127, 26);
		c.add(lbl_rtable1);

		JLabel lbl_rtable2 = new JLabel("Destination");
		lbl_rtable2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_rtable2.setBounds(184, 414, 133, 26);
		c.add(lbl_rtable2);

		JLabel lbl_rtable3 = new JLabel("NextNode");
		lbl_rtable3.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_rtable3.setBounds(295, 414, 104, 26);
		c.add(lbl_rtable3);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(59, 454, 380, 106);
		frame.getContentPane().add(scrollPane);

		ta_rtable = new JTextArea();
		scrollPane.setViewportView(ta_rtable);
		ta_rtable.setEditable(false);
        
		frame.setPreferredSize(new Dimension(500, 650));
		frame.setVisible(true);	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();

	}

   //a default method overridden from the ActionListener Interface
	@Override
    //method to set the outputs to the applet after clicking on the generate Button i.e. Handling the actionEvent
	public void actionPerformed(ActionEvent args0) {
		// TODO Auto-generated method stub
		try 
		{
            //getting the source and destination nodes from the Applet inorder to send it to the program file
			int a = Integer.parseInt(tf_source.getText());
			int b=Integer.parseInt(tf_destination.getText());
			String Fpath=ta_browse.getText();
			if(Fpath=="")
				lbl_error.setText("Please Input the file");
			else
			{
				//Dijkstra dj= new Dijkstra(ta_browse.getText());
				if(dj.Dimplementation(a,b)==0)
				{
					lbl_error.setText("");
					String s=dj.printPath();
					ta_path.setText(s);    //setting the path obtained from program
					ta_distance.setText(""+dj.printDis());//setting the distance obtained from the program
					ta_rtable.setText(dj.printRoute());//setting the routing table from the program
				}
				else
					//lbl_error.setText("Incorrect matrix file");
					JOptionPane.showMessageDialog(null,"Incorrect matrix file" );
					
			}

		}
        //Error Handling in multiple catch Blocks
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.print("no such node exists");
			JOptionPane.showMessageDialog(null,"No such node exists !!" );
			
		}
		catch(NumberFormatException e)
		{
			System.out.println("incorrect source destination");
			JOptionPane.showMessageDialog(null, "Incorrect source destination !!");
			
		}
		catch(NullPointerException e)
		{
			System.out.println("please input the correct file");
			JOptionPane.showMessageDialog(null, "Please input the correct file !!");
			
		}


	}
	public static void main(String[] args) {
		AppletDijkstra a=new AppletDijkstra();

	}
    //Button Bro Class for implementing the action of the browse button within the actionPerformed method
	public class ButtonBro implements ActionListener
	{
        
		public void actionPerformed(ActionEvent args0) {
			// TODO Auto-generated method stub
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
			fc.setFileFilter(filter);
			fc.setFileSelectionMode( JFileChooser.FILES_ONLY );

			if( fc.showOpenDialog(btn_browse) == JFileChooser.APPROVE_OPTION )
			{
				ta_browse.setText(fc.getSelectedFile().getAbsolutePath());
				try 
				{
					dj=new Dijkstra(ta_browse.getText());
					ta_matrix.setText(dj.Display);
				} 
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
