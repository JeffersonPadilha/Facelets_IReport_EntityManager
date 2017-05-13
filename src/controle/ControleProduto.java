package controle;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import entidade.Produto;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import persistencia.ProdutoDao;

@ManagedBean (name = "prodmb")
public class ControleProduto {

	private Produto produto;
	private ProdutoDao daoProd;
	private List<Produto> listaProduto;
	
	public ControleProduto(){
		produto = new Produto();
		listaProduto = new ArrayList<>();
		daoProd = new ProdutoDao();
				
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public ProdutoDao getDaoProd() {
		return daoProd;
	}

	public void setDaoProd(ProdutoDao daoProd) {
		this.daoProd = daoProd;
	}

	public List<Produto> getListaProduto() {
		listaProduto.clear();
		listaProduto.addAll(daoProd.pesquisar());
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}
	
	public void cadastrar(){
		FacesMessage msg = null;
		try {
			daoProd.inserir(produto);
			produto = new Produto();
			msg = new FacesMessage("Produto Cadastrado !");
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = new FacesMessage("Erro : " + e.getMessage());
		}
		FacesContext.getCurrentInstance().addMessage("formcadprod", msg);
	}
	
	public void gerarRelatorio(){
		
		try {
			InputStream arquivo = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/relatorioprod.jasper");
					
			byte pdf[] = JasperRunManager.runReportToPdf(arquivo, null, new JRBeanCollectionDataSource(getListaProduto()));
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponseOutputStream();
			
			ServletOutputStream out = response.getOutputStream();
			out.write(pdf);
			out.flush();
			
			FacesContext.getCurrentInstance().responseComplete();
					
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
