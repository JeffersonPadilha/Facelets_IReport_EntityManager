package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidade.Produto;

public class ProdutoDao {
		
	private EntityManager entityManager;
	
	public ProdutoDao(){
		initEntityManager();
		
	}
	
	private void initEntityManager(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("UnidadePersistMySQL");
		
		if(entityManager == null){
			entityManager = factory.createEntityManager();
			
		}
		
	}
	
	public void inserir(Produto p) throws Exception{
		entityManager.getTransaction().begin();
		entityManager.persist(p);
		entityManager.getTransaction().commit();
	}
	
	public void alterar(Produto p) throws Exception{
		entityManager.getTransaction().begin();
		entityManager.merge(p);
		entityManager.getTransaction().commit();
	}
	
	public void excluir(Produto p) throws Exception{
		entityManager.getTransaction().begin();
		entityManager.remove(p);
		entityManager.getTransaction().commit();
	}

	public List<Produto> pesquisar(){
		return entityManager.createQuery("FROM Produto").getResultList();
	}
	
	public Produto pesquisar(Integer cod){
		return entityManager.find(Produto.class, cod);
	}
}
