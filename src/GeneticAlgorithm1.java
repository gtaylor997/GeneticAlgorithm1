import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GeneticAlgorithm1 {

	public GeneticAlgorithm1(String title) {

	}

	public static void main(String[] args) {
		Simulator sim = new Simulator();
		JFrame frame = new JFrame("Genetic Algorithm 1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(Simulator.WIDTH, Simulator.HEIGHT));
		panel.setLayout(null);

		sim.setBounds(0, 0, Simulator.WIDTH, Simulator.HEIGHT);
		sim.setIgnoreRepaint(true);

		panel.add(sim);

		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		sim.createBufferStrategy(2);
		new Thread(sim).start();
	}

}
