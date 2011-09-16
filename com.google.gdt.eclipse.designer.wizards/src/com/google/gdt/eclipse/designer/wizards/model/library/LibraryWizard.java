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
package com.google.gdt.eclipse.designer.wizards.model.library;

import com.google.gdt.eclipse.designer.wizards.Activator;

import org.eclipse.wb.internal.core.DesignerPlugin;
import org.eclipse.wb.internal.core.utils.jdt.core.CodeUtils;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * Wizard for new GWT library.
 * 
 * @author scheglov_ke
 * @coverage gwt.wizard.ui
 */
public class LibraryWizard extends Wizard implements INewWizard {
  private IPackageFragmentRoot m_sourceFolder;
  private LibraryWizardPage m_libraryPage;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public LibraryWizard() {
    setDefaultPageImageDescriptor(Activator.getImageDescriptor("wizards/library/banner.gif"));
    setWindowTitle("New GWT Library");
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Pages
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  public void addPages() {
    m_libraryPage = new LibraryWizardPage(m_sourceFolder);
    addPage(m_libraryPage);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // INewWizard
  //
  ////////////////////////////////////////////////////////////////////////////
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    m_sourceFolder = null;
    try {
      Object selectedObject = selection.getFirstElement();
      if (selectedObject instanceof IJavaElement) {
        IJavaElement element = (IJavaElement) selectedObject;
        m_sourceFolder = CodeUtils.getPackageFragmentRoot(element);
      }
    } catch (Throwable e) {
      DesignerPlugin.log(e);
    }
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Finish
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean performFinish() {
    try {
      m_libraryPage.createLibrary();
      return true;
    } catch (Throwable e) {
      DesignerPlugin.log(e);
    }
    return false;
  }
}
