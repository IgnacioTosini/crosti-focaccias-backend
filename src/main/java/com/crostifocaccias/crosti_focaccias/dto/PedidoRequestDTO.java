package com.crostifocaccias.crosti_focaccias.dto;

import java.util.List;

public class PedidoRequestDTO {
    private String clientPhone;
    private List<FocacciaCantidad> focaccias;

    public static class FocacciaCantidad {
        private Long focacciaId;
        private int cantidad;

        public FocacciaCantidad() {}

        public FocacciaCantidad(Long focacciaId, int cantidad) {
            this.focacciaId = focacciaId;
            this.cantidad = cantidad;
        }

        public Long getFocacciaId() {
            return focacciaId;
        }

        public void setFocacciaId(Long focacciaId) {
            this.focacciaId = focacciaId;
        }

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
    }

    public PedidoRequestDTO() {}

    public PedidoRequestDTO(String clientPhone, List<FocacciaCantidad> focaccias) {
        this.clientPhone = clientPhone;
        this.focaccias = focaccias;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public List<FocacciaCantidad> getFocaccias() {
        return focaccias;
    }

    public void setFocaccias(List<FocacciaCantidad> focaccias) {
        this.focaccias = focaccias;
    }
}
