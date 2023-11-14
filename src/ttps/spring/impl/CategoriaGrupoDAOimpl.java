package ttps.spring.impl;

import org.springframework.stereotype.Repository;

import ttps.spring.DAO.*;
import ttps.spring.model.*;

@Repository
public class CategoriaGrupoDAOimpl extends GenericDAOimpl<CategoriaGrupo> implements CategoriaGrupoDAO{

	public CategoriaGrupoDAOimpl() {
		super(CategoriaGrupo.class);
	}


}
