/* 
 * Licensed to the soi-toolkit project under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The soi-toolkit project licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.soitoolkit.tools.generator.plugin.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (mpe).
 */

public class StatusPage extends WizardPage {


	private StyledText statusTextArea;

	private ISelection selection;

	/**
	 * Constructor for StatusPage.
	 * 
	 * @param pageName
	 */
	public StatusPage(ISelection selection) {
		super("wizardPage");
		setTitle("SOI Toolkit - Create a new component, work in progress...");
		setDescription("Output from creating the new component");
		setImageDescriptor(ImageDescriptor.createFromFile(this.getClass(), "component-large.png"));
		this.selection = selection;
	}

	/**
	 * Returns a new PrinstStream that can be used to update the status-text-area on this page.
	 * 
	 * @return
	 */
	public PrintStream createStatusPrintStream() {
		return new PrintStream(new OutputStream() {
			StringBuffer sb = new StringBuffer();
			@Override
			public void write(int b) throws IOException {
				if (b == '\n') {
					String str = sb.toString();
					sb.delete(0, sb.length());
					writeLine(str);
				} else {
					sb.append(Character.toString((char) b));
				}
			}
		});
	}
	
	/**
	 * Writes a line to the status-text-area in the GUI-thread
	 * 
	 * @param line
	 */
	protected void writeLine(final String line) {
		getContainer().getShell().getDisplay().syncExec(
			new Runnable() {
				public void run() {
					if (statusTextArea != null) {
						statusTextArea.setText(line + '\n' + statusTextArea.getText()); 
					}
				}
			}
		);
	}
	
	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;
		layout.verticalSpacing = 9;

		statusTextArea = new StyledText(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL);
		statusTextArea.setLayoutData(new GridData(GridData.FILL_BOTH
				| GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));
		statusTextArea.setEditable(false);
		
		initialize();
		dialogChanged();
		setControl(container);
	}


	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
	}


	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete((message == null));
	}
	
}