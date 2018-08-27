package tmt.dsl.formats.context.in;

import com.intellij.psi.PsiElement;

public class ElementInfo {
  private String node;
  private String text;
  int line;

  public ElementInfo(PsiElement psiElement, int line_) {
      text = psiElement.getText();
      node = psiElement.getNode().toString().split(":")[0];
      line = line_;
  }
}