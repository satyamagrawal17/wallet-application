package com.example.walletApplication.registry;

import com.example.walletApplication.enums.ETransactionType;
import com.example.walletApplication.handler.ITransactionHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TransactionHandlerRegistry {
    private final Map<ETransactionType, ITransactionHandler> handlers;

    public TransactionHandlerRegistry(List<ITransactionHandler> handlers) {
        this.handlers = handlers.stream().collect(Collectors.toMap(ITransactionHandler::getType, Function.identity()));
    }

    public ITransactionHandler getHandler(ETransactionType type) {
        return handlers.get(type);
    }
}
