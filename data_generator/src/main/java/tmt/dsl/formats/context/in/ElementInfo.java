package tmt.dsl.formats.context.in;

import tmt.dsl.data.Utils;

import com.intellij.psi.PsiElement;

public class ElementInfo {
  public String node = "";
  //ACTUAL "VALUE" in traces
  public String text = "";
  public int line;
  public String type = "";
  //ACTUAL "VALUE" in traces
  public String ast_type = "";
  public String parent;
  public String child;
  public String class_method;
  public String comparing_method;
  
  public ElementInfo(String type, String value, String text_key) {
    if (type.equals("ast_type")) {
      this.ast_type = value;
      this.comparing_method = "ast_type";
    }
    else if (type.equals("type")) {
      this.type = value;
      this.comparing_method = "type";
    }
    else if (type.equals("class_method")) {
    	this.class_method = value;
        this.comparing_method = "class_method";
    }
    	
    if (text_key != null && !text_key.isEmpty())
      text = text_key;
  }
  
  public boolean equals(Object o2) {
    if (o2 == null)
      return false;
    if (o2 == this) 
      return true;
    if (!(o2 instanceof ElementInfo)) 
      return false;
    if ( comparing_method.equals("class_method") )
    	return Utils.compare(class_method, ((ElementInfo)o2).class_method);
    else if ( comparing_method.equals("ast_type") )
    	return Utils.compare(ast_type, ((ElementInfo)o2).ast_type);
    else if ( comparing_method.equals("type") )
      if (Utils.compare(type, ((ElementInfo)o2).type)) 
        if ( !text.isEmpty() ) 
          /**
           * TODO:
           * Contains or Equals?
           */
          return ((ElementInfo)o2).text.equals(text);
        else
          return true;

    return false;
  }
  
  public int hashCode() {
	  System.exit(1);
    return (type+" "+ast_type+" "+parent+" "+node+" "+text).hashCode();
  }
  
  public String toString() {
    return "type: "+type+", text: "+text+" comparing_method: "+comparing_method;
  }
//
//
//  public ElementInfo(PsiElement psiElement, int line_) {
//      text = psiElement.getText().trim();
//      node = psiElement.getNode().toString();
//      parent = psiElement.getNode().toString();
//      line = line_;
//  }
}
