package com.example.inmobiliariajonathan.ui.contratos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliariajonathan.R;
import com.example.inmobiliariajonathan.databinding.ItemDetallePagoBinding;
import com.example.inmobiliariajonathan.modelo.Contrato;
import com.example.inmobiliariajonathan.modelo.Pago;

import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.ViewHolder> {

    private Context context;
    private List<Pago> pagos;

    public PagosAdapter(Context context, List<Pago> pagos) {
        this.context = context;
        this.pagos = pagos;
    }

    @NonNull
    @Override
    public PagosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemDetallePagoBinding binding = ItemDetallePagoBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PagosAdapter.ViewHolder holder, int position) {
        Pago pago = pagos.get(position);
        holder.bind(pago);
    }

    @Override
    public int getItemCount() {
        return pagos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemDetallePagoBinding binding;
        private Contrato contratoPago;

        public ViewHolder(@NonNull ItemDetallePagoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(view -> {
                if (contratoPago != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("contrato", contratoPago);
                    Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main)
                            .navigate(R.id.pagosFragment, bundle);
                }
            });
        }

        public void bind(Pago pago) {
            binding.tvCodigoPago.setText(String.valueOf(pago.getIdPago()));
            binding.tvNumeroPago.setText(String.valueOf(pago.getNumero()));
            binding.tvCodigoContratoPagos.setText(String.valueOf(pago.getContrato().getIdContrato()));
            binding.tvFechaPago.setText(pago.getFechaDePago());
            binding.tvImporte.setText("$" + pago.getImporte());

            contratoPago = pago.getContrato();
        }
    }
}
