package br.com.smarttech.acs.iget.repository;

import android.content.Context;
import android.os.AsyncTask;

import br.com.smarttech.acs.iget.DAO.PessoaDAO;
import br.com.smarttech.acs.iget.database.IGetRoomDatabase;
import br.com.smarttech.acs.iget.helper.ConfiguracaoFirebase;
import br.com.smarttech.acs.iget.model.Pessoa;

public class PessoaRepository {
    private PessoaDAO mPessoaDAO;
    private Pessoa mPessoas;
    private String idUsuarioLogado;

    public PessoaRepository(Context context){
        IGetRoomDatabase db = IGetRoomDatabase.getDatabase(context);
        mPessoaDAO = db.pessoaDAO();
        idUsuarioLogado = ConfiguracaoFirebase.getIdUsuario();
        mPessoas = mPessoaDAO.recuperarDadosUsuario(idUsuarioLogado);
    }

    public Pessoa pessoaPorId(String ID){

        return mPessoaDAO.recuperarDadosUsuario(ID);
    }

    public void insert(Pessoa pessoa){

        new insertAsyncTask(mPessoaDAO).execute(pessoa);
    }

    public void update(Pessoa pessoa){

        new updateAsyncTask(mPessoaDAO).execute(pessoa);
    }



    private static class insertAsyncTask extends AsyncTask<Pessoa,Void, Void>{
        private PessoaDAO mAsyncTaskDAO;
        insertAsyncTask(PessoaDAO dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Pessoa...params) {
            mAsyncTaskDAO.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Pessoa,Void, Void>{
        private PessoaDAO mAsyncTaskDAO;
        updateAsyncTask(PessoaDAO dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(final Pessoa...params) {
            mAsyncTaskDAO.update(params[0]);
            return null;
        }
    }
}
