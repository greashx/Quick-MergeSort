import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {

    //GUI Components
    private JTextField sizeField;
    private JTextArea mergeSortOutput, quickSortOutput, averageStatsArea;
    private JLabel mergeTimeLabel, quickTimeLabel;
    private JButton runButton, complexityButton;
    private final NameGenerator generator = new NameGenerator();

    //Data lists to store run times for calculating averages
    private final List<Double> mergeSortTimes = new ArrayList<>();
    private final List<Double> quickSortTimes = new ArrayList<>();

    public Main() {
        super("Sorting Tester");
        setupUI();
    }

    private void setupUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // --- Input Panel (Top) ---
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Number of Names:"));
        sizeField = new JTextField("10000", 10);
        inputPanel.add(sizeField);
        runButton = new JButton("Run Sorting");
        inputPanel.add(runButton);
        add(inputPanel, BorderLayout.NORTH);

        // --- Output Panel (Center) ---
        JPanel outputPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        add(outputPanel, BorderLayout.CENTER);

        //Merge Sort Panel
        JPanel mergePanel = new JPanel(new BorderLayout(0, 5));
        mergePanel.add(new JLabel("Merge Sort Results", SwingConstants.CENTER), BorderLayout.NORTH);
        mergeSortOutput = new JTextArea();
        mergeSortOutput.setEditable(false);
        mergePanel.add(new JScrollPane(mergeSortOutput), BorderLayout.CENTER);
        outputPanel.add(mergePanel);

        //Quick Sort Panel
        JPanel quickPanel = new JPanel(new BorderLayout(0, 5));
        quickPanel.add(new JLabel("Quick Sort Results", SwingConstants.CENTER), BorderLayout.NORTH);
        quickSortOutput = new JTextArea();
        quickSortOutput.setEditable(false);
        quickPanel.add(new JScrollPane(quickSortOutput), BorderLayout.CENTER);
        outputPanel.add(quickPanel);

        // --- Bottom Panel ---
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        add(bottomPanel, BorderLayout.SOUTH);

        complexityButton = new JButton("Show Sorting Info");
        bottomPanel.add(complexityButton, BorderLayout.NORTH);

        // NEW: Average stats area
        averageStatsArea = new JTextArea(3, 40);
        averageStatsArea.setEditable(false);
        averageStatsArea.setFont(new Font("Monospaced", Font.BOLD, 12));
        averageStatsArea.setBorder(BorderFactory.createTitledBorder("Run Statistics"));
        bottomPanel.add(new JScrollPane(averageStatsArea), BorderLayout.CENTER);

        // Panel for individual run times
        JPanel timeLabelsPanel = new JPanel(new GridLayout(1, 2));
        mergeTimeLabel = new JLabel("Time: 0 ms");
        quickTimeLabel = new JLabel("Time: 0 ms");
        timeLabelsPanel.add(mergeTimeLabel);
        timeLabelsPanel.add(quickTimeLabel);
        bottomPanel.add(timeLabelsPanel, BorderLayout.SOUTH);

        // Action Listeners
        runButton.addActionListener(e -> runSorting());

        complexityButton.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Merge Sort Complexity: O(n log n)\nQuick Sort Average Complexity: O(n log n)\nQuick Sort Worst Case: O(n^2)",
                "Algorithm Complexity",
                JOptionPane.INFORMATION_MESSAGE));
    }

    private void runSorting() {
        try {
            int size = Integer.parseInt(sizeField.getText());
            if (size <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Generate a single list of names using the provided NameGenerator logic
            String[] names = generator.generateNames(size);

            // --- Merge Sort ---
            String[] mergeNames = names.clone();
            long startTime = System.nanoTime();
            MergeSort.mergeSort(mergeNames);
            long endTime = System.nanoTime();
            double mergeTime = (endTime - startTime) / 1_000_000.0;
            mergeSortTimes.add(mergeTime); // Add to list
            mergeTimeLabel.setText(String.format("Time: %.2f ms", mergeTime));
            updateDisplayText(mergeSortOutput, mergeNames);

            // --- Quick Sort ---
            String[] quickNames = names.clone();
            startTime = System.nanoTime();
            QuickSort.quickSort(quickNames, 0, quickNames.length - 1);
            endTime = System.nanoTime();
            double quickTime = (endTime - startTime) / 1_000_000.0;
            quickSortTimes.add(quickTime); // Add to list
            quickTimeLabel.setText(String.format("Time: %.2f ms", quickTime));
            updateDisplayText(quickSortOutput, quickNames);

            // --- UPDATE AVERAGE STATS ---
            updateAverageTimes();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDisplayText(JTextArea textArea, String[] sortedNames) {
        // Display sorted results (limited to 100 entries for readability)
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < Math.min(100, sortedNames.length); i++) {
            text.append(sortedNames[i]).append("\n");
        }
        if (sortedNames.length > 100) {
            text.append("...");
        }
        textArea.setText(text.toString());
        textArea.setCaretPosition(0); // Scroll to top
    }

    private void updateAverageTimes() {
        // Calculate averages using streams
        double avgMerge = mergeSortTimes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double avgQuick = quickSortTimes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        // Update the text area
        averageStatsArea.setText(String.format(
                "Total Runs: %d\nAverage Merge Sort Time: %.2f ms\nAverage Quick Sort Time: %.2f ms",
                mergeSortTimes.size(),
                avgMerge,
                avgQuick
        ));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main main = new Main();
            main.setVisible(true);
        });
    }
}