package com.gramazski.craps.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by gs on 06.03.2017.
 */

public class ChooseTag extends TagSupport {
    private int decisionValue;

    public void setDecisionValue(int decisionValue) {
        this.decisionValue = decisionValue;
    }

    /**
     * @return
     * @throws JspTagException
     */
    @Override
    public int doStartTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();
            if (decisionValue > 0){
                out.write("<span class=\"bg-success\">");
            }
            else {
                out.write("<span class=\"bg-danger\">");
            }
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

    /**
     * @return
     * @throws JspTagException
     */
    @Override
    public int doAfterBody() throws JspTagException {
        try {
            pageContext.getOut().write("</span>");
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }

        return SKIP_BODY;
    }

    /**
     * @return
     * @throws JspTagException
     */
    @Override
    public int doEndTag() throws JspTagException {
        return EVAL_PAGE;
    }
}
