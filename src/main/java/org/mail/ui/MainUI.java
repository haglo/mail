/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.mail.ui;

import org.mail.service.PmailService;
import org.mail.ui.view.MailDetailView;
import org.mail.ui.view.MailListView;
import org.mail.ui.view.MailMainView;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@HtmlImport("frontend://styles/shared-styles.html")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@Theme(value = Lumo.class)
@Route("")
public class MainUI extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private MailListView mailListView;

	public MainUI(@Autowired TopMenu topMenu, @Autowired MailMainView mailMainView,  @Autowired MailDetailView mailDetailView, @Autowired PmailService service) {
		mailListView = new MailListView(service,mailMainView);
		mailMainView.getMailContentRightPart().addToPrimary(mailListView);
		mailMainView.getMailContentRightPart().addToSecondary(mailDetailView);
		add(topMenu);
		add(mailMainView);
		addClassName("main-layout");
	}

}
