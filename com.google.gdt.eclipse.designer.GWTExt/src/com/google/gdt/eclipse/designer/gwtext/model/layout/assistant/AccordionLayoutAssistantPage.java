/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.gdt.eclipse.designer.gwtext.model.layout.assistant;

import com.google.gdt.eclipse.designer.gwtext.model.layout.AccordionLayoutInfo;

import org.eclipse.wb.internal.core.utils.ui.GridDataFactory;
import org.eclipse.wb.internal.core.utils.ui.GridLayoutFactory;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Assistant for GWT-Ext {@link AccordionLayoutInfo}.
 * 
 * @author sablin_aa
 * @coverage GWTExt.model.layout.assistant
 */
public final class AccordionLayoutAssistantPage extends AbstractGwtExtAssistantPage {
  public AccordionLayoutAssistantPage(Composite parent, AccordionLayoutInfo selection) {
    super(parent, selection);
    GridLayoutFactory.create(this).columns(2);
    // options
    {
      Group optionsGroup =
          addBooleanProperties(this, "Options", new String[][]{
              new String[]{"activeOnTop", "activeOnTop"},
              new String[]{"animate", "animate"},
              new String[]{"autoWidth", "autoWidth"},
              new String[]{"collapseFirst", "collapseFirst"},
              new String[]{"fill", "fill"},
              new String[]{"hideCollapseTool", "hideCollapseTool"},
              new String[]{"titleCollapse", "titleCollapse"},
              new String[]{"sequence", "sequence"},
              new String[]{"renderHidden", "renderHidden"}});
      GridLayoutFactory.create(optionsGroup).columns(2);
      GridDataFactory.create(optionsGroup).spanH(2);
    }
    addStringProperty(this, "extraCls");
    addStringProperty(this, "spacing");
  }
}