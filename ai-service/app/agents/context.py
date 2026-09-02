from dataclasses import dataclass, field


@dataclass
class BankingContext:

    access_token: str = field(repr=False)
