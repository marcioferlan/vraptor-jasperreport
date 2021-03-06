package br.com.caelum.vraptor.jasperreports.util;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.jasperreports.Report;
import br.com.caelum.vraptor.jasperreports.ReportFormatResolver;
import br.com.caelum.vraptor.jasperreports.exporter.ReportExporter;
import br.com.caelum.vraptor.jasperreports.formats.ExportFormat;

import com.lowagie.text.pdf.codec.Base64;

@RequestScoped
public class DefaultReportDataURIBuilder implements ReportDataURIBuilder {

	@Inject private ReportExporter exporter;
	@Inject private ReportFormatResolver resolver;

	/**
	 * The data URIs have the following syntax:
	 * data:[<mimetype>][;charset][;base64],<data>
	 */
	public String build(Report report, ExportFormat format) {
		byte[] content = exporter.export(report).to(format);
		StringBuilder URI = new StringBuilder("data:");
		URI.append(format.getContentType());
		URI.append(";charset=").append("utf-8");
		URI.append(";base64,").append(Base64.encodeBytes(content));
		return URI.toString();
	}

	public String build(Report report) {
		return build(report, resolver.getExportFormat());
	}

}
