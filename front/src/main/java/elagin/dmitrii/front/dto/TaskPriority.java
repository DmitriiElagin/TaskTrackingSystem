package elagin.dmitrii.front.dto;

/**
 * @author Dmitrii Elagin
 * Date 18.05.2023 12:47
 */
public enum TaskPriority {
  LOW("Низкий"),
  MEDIUM("Средний"),
  HIGH("Высокий");

  private final String label;

  TaskPriority(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
