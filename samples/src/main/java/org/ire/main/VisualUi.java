package org.ire.main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.ire.visualization.WordCount;

public class VisualUi extends JPanel {

	public JRadioButton[] features;

	public boolean checkedFields[];
	public String selected;
	JRadioButton bargraph, wordcloud;
	ButtonGroup bg, bg1;
	public static int clusterno = -1;
	public static boolean isWorldCloud = false;
	public static boolean isBarGraph = false;

	// Create a form with the specified labels, tooltips, and sizes.
	public VisualUi(String[] labels) {
		super(new BorderLayout());

		JPanel fieldPanel = new JPanel(new GridLayout(12, 1));
		// JPanel imagePanel = new JPanel(new FlowLayout());
		this.setSize(700, 700);
		ImageIcon image = new ImageIcon("image/cover3.jpg");
		bargraph = new JRadioButton("bargraph");
		wordcloud = new JRadioButton("wordcloud");
		bg = new ButtonGroup();
		bg.add(bargraph);
		bg.add(wordcloud);

		JLabel label = new JLabel("", image, JLabel.CENTER);
		JPanel imgpanel = new JPanel(new BorderLayout());
		imgpanel.add(label, BorderLayout.CENTER);

		add(fieldPanel, BorderLayout.CENTER);
		add(imgpanel, BorderLayout.EAST);

		features = new JRadioButton[labels.length];
		checkedFields = new boolean[labels.length];
		bg1 = new ButtonGroup();

		for (int i = 0; i < labels.length; i += 1) {
			final int j = i;
			features[i] = new JRadioButton(labels[i]);
			features[i].addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (features[j].isSelected()) {
						checkedFields[j] = true;
					} else {
						checkedFields[j] = false;
					}
				}
			});
			bg1.add(features[i]);
			JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p.add(features[i]);
			fieldPanel.add(p);
		}

		JLabel lab2 = new JLabel(" -----------------------------", JLabel.RIGHT);

		fieldPanel.add(lab2);

		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(bargraph);
		fieldPanel.add(p);

		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.add(wordcloud);
		fieldPanel.add(p);

	}

	public String getText(int i) {
		return (features[i].getText());
	}

	public static void analyseResult(int clusterCount) {
		JOptionPane.showMessageDialog(null, "Things will run properly");
	}

	public static void main(String[] args) {
		int num = 5;

		String[] labels = new String[num];
		// { "   cluster0","   cluster1","   cluster2", };

		for (int i = 0; i < num; i++) {
			labels[i] = "   cluster" + i;
		}

		final VisualUi form = new VisualUi(labels);

		JButton submit = new JButton("Get Graph");

		submit.addActionListener(new ActionListener() {
			final boolean[] cfields = form.checkedFields;

			public void actionPerformed(ActionEvent e) {
				System.out.println("Selected fields : ");

				for (int i = 0; i < cfields.length; i++) {
					if (cfields[i]) {
						clusterno = i;

					}
				}
				if (form.wordcloud.isSelected()) {
					isWorldCloud = true;
					System.out.println("cluster"+clusterno);
					WordCount.ivoke("cluster"+clusterno, null);
				}

				if (form.bargraph.isSelected()) {

					isBarGraph = true;
				}

			}
		});

		JFrame f = new JFrame("Document clustering");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(form, BorderLayout.NORTH);
		JPanel p = new JPanel();
		p.add(submit);
		f.getContentPane().add(p, BorderLayout.SOUTH);
		f.pack();
		f.setVisible(true);
	}
}
