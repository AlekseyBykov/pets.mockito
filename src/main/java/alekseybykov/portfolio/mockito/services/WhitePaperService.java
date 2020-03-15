package alekseybykov.portfolio.mockito.services;

import alekseybykov.portfolio.mockito.dependencies.external.DocumentService;

import java.util.ArrayList;
import java.util.List;

public class WhitePaperService {

	public WhitePaperService(DocumentService documentService) {
		this.documentService = documentService;
	}

	private DocumentService documentService;

	public List<String> filterWhitePapers(String whitePaperName) {
		List<String> filteredWhitePapers = new ArrayList<>();
		List<String> allWhitePapers = documentService.searchDocuments(whitePaperName);
		for (String whitePaper : allWhitePapers) {
			if (whitePaper.contains(whitePaperName)) {
				filteredWhitePapers.add(whitePaper);
			}
		}
		return filteredWhitePapers;
	}
}
