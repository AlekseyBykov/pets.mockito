package alekseybykov.portfolio.mockito.services;

import alekseybykov.portfolio.mockito.dependencies.external.DocumentService;
import alekseybykov.portfolio.mockito.utils.Marker;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * @author Aleksey Bykov
 * @since 15.03.2020
 */
public class WhitePaperService {

	public WhitePaperService(DocumentService documentService) {
		this.documentService = documentService;
	}

	private DocumentService documentService;

	public List<String> filterWhitePapers(String whitePaperName) {
		List<String> filteredWhitePapers = new ArrayList<>();
		List<String> allWhitePapers = documentService.searchDocumentsByName(whitePaperName);
		for (String whitePaper : allWhitePapers) {
			if (StringUtils.containsIgnoreCase(whitePaper, whitePaperName)) {
				filteredWhitePapers.add(whitePaper);
			}
		}
		return filteredWhitePapers;
	}

	public void deleteWhitePapers(String whitePaperName) {
		List<String> allWhitePapers = documentService.searchDocumentsByName(whitePaperName);
		for (String whitePaper : allWhitePapers) {
			if (StringUtils.containsIgnoreCase(whitePaper, whitePaperName)) {
				documentService.deleteDocumentByName(whitePaper);
			}
		}
	}

	public String markWhitePaper(String whitePaperName) {
		return format("%s#%s", whitePaperName, Marker.getMark());
	}
}
