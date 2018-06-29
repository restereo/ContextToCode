package tmt.code.snippets.stackoverflow;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.jsoup.Jsoup;
import org.jsoup.examples.HtmlToPlainText;

import tmt.conf.Utils;

/***
 * @Id:6
@PostTypeId:1
@AcceptedAnswerId:31
@CreationDate:2008-07-31T22:08:08.620
@Score:249
@ViewCount:16006
@Body:<p>I have an absolutely positioned <code>div</code> containing several children, one of which is a relatively positioned <code>div</code>. When I use a <strong>percentage-based width</strong> on the child <code>div</code>, it collapses to '0' width on <a href="http://en.wikipedia.org/wiki/Internet_Explorer_7" rel="noreferrer">Internet&nbsp;Explorer&nbsp;7</a>, but not on Firefox or Safari.</p> <p>If I use <strong>pixel width</strong>, it works. If the parent is relatively positioned, the percentage width on the child works.</p> <ol> <li>Is there something I'm missing here?</li> <li>Is there an easy fix for this besides the <em>pixel-based width</em> on the child?</li> <li>Is there an area of the CSS specification that covers this?</li> </ol>
@OwnerUserId:9
@LastEditorUserId:63550
@LastEditorDisplayName:Rich B
@LastEditDate:2016-03-19T06:05:48.487
@LastActivityDate:2016-03-19T06:10:52.170
@Title:Percentage width child element in absolutely positioned parent on Internet Explorer 7
@Tags:<html><css><css3><internet-explorer-7>
@AnswerCount:5
@CommentCount:0
@FavoriteCount:10
-
row
@Id:7
@PostTypeId:2
@ParentId:4
@CreationDate:2008-07-31T22:17:57.883
@Score:395
@Body:<p>An explicit cast to double like this isn't necessary:</p> <pre><code>double trans = (double) trackBar1.Value / 5000.0; </code></pre> <p>Identifying the constant as <code>5000.0</code> (or as <code>5000d</code>) is sufficient:</p> <pre><code>double trans = trackBar1.Value / 5000.0; double trans = trackBar1.Value / 5000d; </code></pre>
@OwnerUserId:9
@LastEditorUserId:4020527
@LastEditDate:2017-12-16T05:06:57.613
@LastActivityDate:2017-12-16T05:06:57.613
@CommentCount:0
 * @author user
 *
 */
@XmlRootElement(name = "row")
//@XmlAccessorType(XmlAccessType.FIELD)
public class Row {
  @XmlAttribute(name = "CreationDate")
  String creation_date;
  @XmlAttribute(name = "Body")
  String body;
  @XmlAttribute(name = "LastEditDate")
  String last_edit_date;
  @XmlAttribute(name = "LastActivityDate")
  String last_activity_date;
  @XmlAttribute(name = "Title")
  String title;
  @XmlAttribute(name = "Tags")
  String tags;
  ArrayList<String> tags_arr = new ArrayList<>();
  @XmlAttribute(name = "CommentCount")
  int comment_count;
  @XmlAttribute(name = "LastEditorUserId")
  int last_editor_user_id;
  @XmlAttribute(name = "OwnerUserId")
  int owner_user_id;
  @XmlAttribute(name = "ParentId")
  int parent_id;
  @XmlAttribute(name = "Score")
  int score;
  @XmlAttribute(name = "ViewCount")
  int view_count;
  @XmlAttribute(name = "PostTypeId")
  int post_type_id;
  @XmlAttribute(name = "AcceptedAnswerId")
  int accepted_answer_id;
  @XmlAttribute(name = "Id")
  int id;
  @XmlAttribute(name = "FavoriteCount")
  int favorite_count;
  @XmlAttribute(name = "AnswerCount")
  int answer_count;
  ArrayList<String> code;
  String stripped;

  public int getId() {
    return id;
  }

  public void parse() {
    tags_arr = Utils.parse(tags, "<", ">");
    code = Utils.parse(body, "<code>", "</code>");
    stripped = new HtmlToPlainText().getPlainText(Jsoup.parse(body));
  }

  public String toString() {
    return "Id: "+id+" Tags: "+tags_arr;
  }

  public ArrayList<String> getTags() {
    return tags_arr;
  }

  public String getBody() {
    return body;
  }

  public String getTitle() {
    return title;
  }

  public int getParentId() {
    return parent_id;
  }

  public Integer getScore() {
    return score;
  }

  public ArrayList<String> getCode() {
    return code;
  }
  
  public String getStripped() {
    return stripped;
  }
}
