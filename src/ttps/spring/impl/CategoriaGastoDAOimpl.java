package ttps.spring.impl;


import org.springframework.stereotype.Repository;

import ttps.spring.DAO.*;
import ttps.spring.model.*;

@Repository
public class CategoriaGastoDAOimpl extends GenericDAOimpl<CategoriaGasto> implements CategoriaGastoDAO{

	public CategoriaGastoDAOimpl() {
		super(CategoriaGasto.class);
	}


}
